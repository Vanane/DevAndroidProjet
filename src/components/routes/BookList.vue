<template>
    <div>
        <span>Search for an author</span>
        <input id="search" ref="searchBar" type="text" @keypress="this.onPrompt()"/>
        <input type="submit" value="Go"/>
        <br><br>
        <div id="result" class="columns is-multiline">
            <Book class="column is-full-mobile is-half-tablet is-one-third-desktop is-one-quarter-widescreen is-one-quarter-fullhd"
                v-for="b in bookList" v-bind:key="b.id" 
                :bookTitle="b.volumeInfo.title"
                :bookAuthor="b.volumeInfo.authors"
                :bookDate="b.volumeInfo.publishedDate"
                :bookResume="b.volumeInfo.description"
            />
        </div>
    </div>
</template>

<script>
import Book from "../Book.vue";
export default {
    name:"Book list",
    components:
    {
        Book
    },
    data()
    {
        return {
            bookList: [],
            searchTimer: {}
        }
    },
    methods: {
        onPrompt()
        {
            clearTimeout(this.searchTimer);
            this.searchTimer = setTimeout(() => { this.getBooks(this.getSearchPrompt()) }, 1000/3);
        },

        getBooks(author)
        {
                fetch("https://www.googleapis.com/books/v1/volumes?q=search+inauthor:" + author)
                    .then(e => e.json() )
                    .then(l => { this.bookList = l.items; })
        },

        getSearchPrompt()
        {
            return this.$refs.searchBar.value;
        }
    },
    mounted()
    {
        if(this.$route.query.author)
        {
            this.$refs.searchBar.value = this.$route.query.author;
            this.getBooks(this.getSearchPrompt());
        }
    }
}

</script>

<style>

</style>