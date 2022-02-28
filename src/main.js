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
        /**
            Retourne le livre correspondant à l'id depuis l'API Google
         */
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

        /**
            Retourne le livre correspondant à l'id depuis le cache local
         */
        getBookInRepository(id)
        {
            if(!id) return;
            for(var [, v] of this.bookRepository)
            {
                if(v.id == id) return v;
            }
            return null;
        },

        /**
            Retourne une liste de livres par filtres depuis l'api Google
         */
        async searchBooks(author, title, categories)
        {
            /*
            var books = this.searchBooksInRepository(author, title, categories);

            if(books.length > 0) return books;
            */
            
            var books = new Array();

            var url = "https://www.googleapis.com/books/v1/volumes?q=search";
            if(author)
                url = url + "+inauthor:" + author;
            if(title)
                url  = url + "+intitle:" + title;
                if(categories)
                url = url + "+subject:" + categories;
            return await fetch(url)
                .then(e => e = e.json())
                .then(e => {
                    e.items.map(el => {
                        this.addToBookRepository(el);
                        books.push(el);
                    });
                    return e.items;
                }
            ).catch(() => { return [] });
        },

        /**
            Ajoute un livre au cache local   
         */
        addToBookRepository(book)
        {
            this.bookRepository.set(book.id, book.volumeInfo);
        },

        /**
            Retourne une liste de livres filtrés depuis le cache local
         */
        searchBooksInRepository(author, title, categories)
        {
            var books = [];
            this.bookRepository.forEach(e => {
                var found = false;
                if(author) found = e.authors?.includes(author);
                if(title) found = e.title?.includes(title);
                if(categories) found = e.categories?.toString().includes(categories);
                if(found)
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
