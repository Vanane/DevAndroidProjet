<template>
    <div>
        <!--TODO : ajouter la recherche par titre, par éditeur, peut-être par genre avec une autre API, en croisant l'ISBN ?-->
        <p class="title is-2">Search for books</p>
        <div class="field has-text-justified">
            <label class="label">Title</label>
            <div class="control">
                <input ref="searchTitle" class="input" type="text" placeholder="A nice book" @keypress="this.onPrompt()">
            </div>
        </div>
        <div class="field has-text-justified">
            <label class="label">Author</label>
            <div class="control">
                <input ref="searchAuthor" class="input" type="text" placeholder="A good author" @keypress="this.onPrompt()">
            </div>
        </div>
        <div class="field has-text-justified">
            <label class="label">Categories</label>
            <div class="control">
                <input ref="searchCategories" class="input" type="text" placeholder="Sum categoriz" @keypress="this.onPrompt()">
            </div>
        </div>
        <p class="title is-3 has-text-justified">Results :</p>
        <div v-if="!this.bookList || this.bookList.length < 1">No volume was found.</div>
        <div v-else id="result" class="columns is-multiline">
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
            this.searchTimer = setTimeout(() => {
                    this.searchBooks(this.getAuthorPrompt(), this.getTitlePrompt(), this.getCategoriesPrompt())
                    .then(e => this.bookList = e);
                }, 666);
        },

        getAuthorPrompt()
        {
            return this.$refs.searchAuthor.value;
        },

        getTitlePrompt()
        {
            return this.$refs.searchTitle.value;
        },

        getCategoriesPrompt()
        {
            return this.$refs.searchCategories.value;
        },

        refreshResearch(author)
        {
            this.$refs.searchAuthor.value = author;
            this.$refs.searchTitle.value = author;
            this.$refs.searchCategories.value = author;
            this.searchBooks(this.getAuthorPrompt(), this.getTitlePrompt(), this.getCategoriesPrompt());
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

.searchForm {
    
}
    
.searchForm input {
    float:left;
    font-size:1.5em;
    padding: 0.25em;
    border:none;
    border-bottom:solid 2px black;
    background-color: #eee;
}

.searchForm input, .searchForm p {
    display:inline-block;
    width:40%;
}

.searchForm .full {
    width:100%;
}
</style>