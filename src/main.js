import { createApp } from 'vue'
import App from './App.vue'
import router  from './router'

const app = createApp(App);

/* Gérer la watchlist dans le localStorage */
app.mixin({
    name: "localStorageManager",
    methods:
    {
        init()
        {
            var books = localStorage.getItem("bookWatchlist");
            var movies = localStorage.getItem("movieWatchlist");

            if(!books) localStorage.setItem("bookWatchlist",  JSON.stringify([]));
            if(!movies) localStorage.setItem("movieWatchlist", JSON.stringify([]));
        },

        /***********/
        /*  Books  */
        /***********/

        getBookWatchlist()
        {
            return JSON.parse(localStorage.getItem("bookWatchlist"));
        },

        getBookFromWatchlist(id)
        {
            var books = JSON.parse(localStorage.getItem("bookWatchlist"));
            if(books)
                return books.find(e => e.bookId == id);
            return null;
        },

		addBookToWatchlist(id)
		{
            console.log("adding" + id);
			this.init();
			if(!this.getBookFromWatchlist(id))
            {
                var watchlist = JSON.parse(localStorage.getItem("bookWatchlist"));
                watchlist.push({ bookId: id, watched:false });
                localStorage.setItem("bookWatchlist", JSON.stringify(watchlist));
            }
		},

		removeBookFromWatchlist(id)
		{
            this.init();
			if(this.getBookFromWatchlist(id))
            {
                var watchlist = JSON.parse(localStorage.getItem("bookWatchlist"));
                watchlist.splice(watchlist.findIndex(e => e.bookId == id), 1);
                localStorage.setItem("bookWatchlist", JSON.stringify(watchlist));
            }
		},

        /************/
        /*  Movies  */
        /************/

        getMovieFromWatchlist(id)
        {
            var movies = JSON.parse(localStorage.getItem("movieWatchlist"));
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
