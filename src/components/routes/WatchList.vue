<template>
    <p class="title is-1">My watchlist</p>
    <div v-if="!isBookEmpty">
        <div id="bookwatchlist" class="columns is-multiline">
            <Book class="column is-full-mobile is-one-third-tablet is-one-third-desktop is-one-quarter-widescreen is-one-fifth-fullhd"
                v-for="b in bookDetails" v-bind:key="b.id" 
                :bookId="b.id"
                :bookTitle="b.volumeInfo.title"
                :bookAuthor="b.volumeInfo.authors"
                :bookPublisher="b.volumeInfo.publisher"
            />
        </div>
    </div>
    <div v-else>
        <p class="title is-4">Your book watchlist is empty.</p>
    </div>
</template>

<script>
import Book from '../Book.vue';
export default {
    name:'WatchList',
    components:{
        Book
    },
    data() {
        return {
            bookWatchlist: [],
            bookDetails: []
        }
    },

    beforeMount() {
        this.bookWatchlist = this.getBookWatchlist();
        this.bookWatchlist.map(e => {
            this.getBook(e.bookId).then(b => this.bookDetails.push(b));
        });
    },

    computed: {
        isBookEmpty() {
            if(!this.bookWatchlist) return true;
            return this.bookWatchlist.length <= 0;
        },

        isMovieEmpty() {
            return true; //TODO
        }
    }
    
}
</script>

<style>

</style>