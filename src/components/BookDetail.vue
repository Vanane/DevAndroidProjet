<template>
	<div class="columns is-multiline">
		<div class="column is-one-third-desktop is-full-mobile">
			<img v-bind:src="bookCover" class="bookcover"/>
		</div>
		<div class="column is-two-third-desktop is-full-mobile">
			<p class="title is-2">{{bookTitle}}</p>
			<p class="title is-3">By {{bookAuthor}}</p>
			<p class="title is-4">Published on {{bookDate ?? "unknown"}} by {{bookPublisher ?? "unknown"}}.</p>
		<span v-if="bookAuthor">
				<router-link :to="{ name: 'BookList', query: { author: bookAuthor } }">More of the same author</router-link>
		</span>
		<p>{{bookResume}}</p>
		<p  v-if="!this.isInWatchlist"> <a class="a-button" @click="addBook">Add</a> </p>
		<p  v-else> <a class="a-button" @click="removeBook">Remove</a> </p>
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
		bookCover : String
	},
	mounted() {
		this.isInWatchlist = (this.getBookFromWatchlist(this.bookId) != null);
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