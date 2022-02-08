<template>
    <div>
        <!--TODO : ajouter la recherche par titre, par éditeur, peut-être par genre avec une autre API, en croisant l'ISBN ?-->
        <p class="title is-2">Search for an author</p>
        <p><input id="search" class="search" ref="searchBar" type="text" @keypress="this.onPrompt()"/></p>
        <br><br>
        <div id="result" class="columns is-multiline">
            <Book class="column is-full-mobile is-one-third-tablet is-one-third-desktop is-one-quarter-widescreen is-one-fifth-fullhd"
                v-for="b in bookList" v-bind:key="b.id" 
                :bookId="b.id"
                :bookTitle="b.volumeInfo.title"
                :bookAuthor="b.volumeInfo.authors"
                :bookPublisher="b.volumeInfo.publisher"
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
            searchTimer: {},
            showList: true
        }
    },
    methods: {
        onPrompt()
        {
            clearTimeout(this.searchTimer);
            this.searchTimer = setTimeout(() => { this.searchBooks(this.getSearchPrompt()).then(e => this.bookList = e); }, 666);
        },

        getSearchPrompt()
        {
            return this.$refs.searchBar.value;
        },

        refreshResearch(author)
        {
            this.$refs.searchBar.value = author;
            this.searchBooks(this.getSearchPrompt());
        }
    },
    mounted()
    {
        var author = this.$route.query.author ?? this.$route.params.author;
        if(author)        
            this.refreshResearch(author);
    }
}

</script>

<style>
.search {
    width:80%;
    font-size:1.5em;
    padding: 0.25em;
    border:none;
    border-bottom:solid 2px black;
    background-color: #eee;
}
</style>