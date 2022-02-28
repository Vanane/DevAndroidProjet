<template>
	<div class="columns is-multiline">
		<div class="column is-one-third-desktop is-full-mobile">
			<img v-bind:src="bookCover" class="bookcover"/>
		</div>
		<div class="column is-two-third-desktop is-full-mobile">
			<p class="title is-2">{{bookTitle}}</p>
			<p class="title is-3">By {{this.getAuthorString()}}</p>
			<p class="title is-4">Published on {{bookDate ?? "unknown"}} by {{bookPublisher ?? "unknown"}}.</p>
			<p class="title is-5">Categories : {{this.getBookCategoriesString()}}</p>
        <p class="title is-4 has-text-justified">Description : </p>
		<p ref="bookResume" class="has-text-justified">{{bookResume}}</p>
		<span v-if="bookAuthor">
				<router-link :to="{ name: 'BookList', query: { author: bookAuthor } }">More of the same author</router-link> | 
		</span>
		<span v-if="!this.isInWatchlist"> <a class="a-button" @click="addBook">Add to watchlist</a> </span>
		<span  v-else> <a class="a-button" @click="removeBook">Remove from watchlist</a> </span>
		</div>
	</div>
</template>

<script>
export default {
	name: "BookDetail",
	data()
	{
		return {
			isInWatchlist: false
		}
	},
	
	props: {
		bookId: String,
		bookTitle : String,
		bookAuthor : Array,
		bookPublisher : String,
		bookDate : Date,
		bookEditor : String,
		bookResume : String,
		bookCover : String,
        bookCategories: Array
	},
	mounted() {
		this.getBookFromWatchlist(this.bookId)
		.then(e => this.isInWatchlist = e != null);
        this.parseHTMLDescription();
	},

	methods:
	{
		addBook()
		{
			this.addBookToWatchlist(this.bookId);
			this.isInWatchlist = true;
		},

		removeBook()
		{
			this.removeBookFromWatchlist(this.bookId);
			this.isInWatchlist = false;
		},

        parseHTMLDescription()
        {
            if(this.bookResume)
                this.$.refs.bookResume.innerHTML = this.bookResume;
            else
                this.$refs.bookResume.innerHTML = "No description";
        },

        getAuthorString()
        {
            if(!this.bookAuthor) return "Unknown";
            if(this.bookAuthor.length > 1)
                return this.bookAuthor.toString().substring(0, this.bookAuthor.toString().lastIndexOf(',')) + " and " + this.bookAuthor[this.bookAuthor.length - 1].toString();
            return this.bookAuthor.toString();
        },

        getBookCategoriesString()
        {
            return this.bookCategories ? this.bookCategories.join(', ') : "None";
        }
	}
}
</script>

<style>
.bookcover {
		width:100%;
		height:auto;
}

.a-button {
	cursor: pointer;

}
</style>