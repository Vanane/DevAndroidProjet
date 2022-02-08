import { createApp } from 'vue'
import App from './App.vue'
import router  from './router'
import { Storage } from '@capacitor/storage';

const app = createApp(App);

/* Gérer la watchlist dans le localStorage */
app.mixin({
    name: "localStorageManager",
    methods:
    {
        async init()
        {
            var books = (await Storage.get({ key: 'bookWatchlist' })).value;
            var movies = (await Storage.get({ key: 'bookWatchlist' })).value;
            
            if(!books) await Storage.set({ key: 'bookWatchlist', value: JSON.stringify([]) });
            if(!movies) await Storage.set({ key: 'movieWatchlist', value: JSON.stringify([]) });
        },

        /***********/
        /*  Books  */
        /***********/

        async getBookWatchlist()
        {
            return JSON.parse((await Storage.get({ key: 'bookWatchlist' })).value);
        },

        async getBookFromWatchlist(id)
        {
            var books = JSON.parse((await Storage.get({ key: 'bookWatchlist' })).value);
            if(books)
                return books.find(e => e.bookId == id);
            return null;
        },

		async addBookToWatchlist(id)
		{
			this.init();
			if(! (await this.getBookFromWatchlist(id)))
            {
                var watchlist = JSON.parse((await Storage.get({ key: 'bookWatchlist' })).value);
                watchlist.push({ bookId: id, watched:false });
                await Storage.set({ key: 'bookWatchlist', value: JSON.stringify(watchlist) });
            }
		},

		async removeBookFromWatchlist(id)
		{
            this.init();
			if((await this.getBookFromWatchlist(id)))
            {
                var watchlist = JSON.parse((await Storage.get({ key: 'bookWatchlist' })).value);
                watchlist.splice(watchlist.findIndex(e => e.bookId == id), 1);
                await Storage.set({ key: 'bookWatchlist', value: JSON.stringify(watchlist) });
            }
		},

        /************/
        /*  Movies  */
        /************/

        async getMovieFromWatchlist(id)
        {
            var movies = JSON.parse((await Storage.get({ key: 'movieWatchlist' })).value);
            if(movies)
                return movies.find(e => e.movieId == id);
            return null;
        }

    }
});

/* Gérer les livres */
app.mixin({
    name:"BookRepository",
    data() {
        return {
            bookRepository: new Map()
        }
    },
    methods: {
        async getBook(id)
        {
            if(!id) return;
            var book = this.getBookInRepository(id);
            if(book) return book;

            var url = "https://www.googleapis.com/books/v1/volumes/" + id;
            return await fetch(url)
                .then(e => e = e.json())
                .then(e => {
                    this.bookRepository.set(e.id, e);
                    return e;
                }
            );
        },

        getBookInRepository(id)
        {
            if(!id) return;
            for(var [, v] of this.bookRepository)
            {
                if(v.id == id) return v;
            }
            return null;
        },

        async searchBooks(author, title)
        {
            var books = this.searchBooksInRepository(author, title);
            if(books.length > 0) return books;
             
            var url = "https://www.googleapis.com/books/v1/volumes?q=search";
            if(author)
                url = url + "+inauthor:" + author;
            if(title)
                url  = url + "+intitle:" + title;

            return await fetch(url)
                .then(e => e = e.json())
                .then(e => {
                    e.items.map(el => {
                        this.bookRepository.set(el.id, el);
                        books.push(el);
                    });
                    return e.items;
                }
            );
        },

        searchBooksInRepository(author, title)
        {
            var books = [];
            this.bookRepository.forEach(e => {
                if(e.author == author && e.title == title)
                    books.push(e);
            })
            return books;
        }

    }
});

/* Gérer les films */
app.mixin({
    name:"movieRepository",    
    methods:{}
});





app.use(router).mount('#app')
