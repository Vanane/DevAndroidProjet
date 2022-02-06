<template>
	<div class="columns is-multiline">
		<div class="column is-one-third-desktop is-full-mobile">
			<img v-bind:src="bookCover" class="bookcover"/>
		</div>
		<div class="column is-two-third-desktop is-full-mobile">
			<p class="title is-2">{{bookTitle}}</p>
			<p class="title is-3">By {{bookAuthor}}, published on {{bookDate ?? "unknown"}} by {{bookPublisher ?? "unknown"}}.</p>
		<span v-if="bookAuthor">
				<router-link :to="{ name: 'BookList', query: { author: bookAuthor } }">More of the same author</router-link>
		</span>
		<p>{{bookResume}}</p>
		</div>
	</div>
</template>

<script>
export default {
		name: "BookDetail",
		props: {
				bookTitle : String,
				bookAuthor : String,
				bookPublisher : String,
				bookDate : Date,
				bookEditor : String,
				bookResume : String,
				bookCover : String
		}
}
</script>

<style>
.bookcover {
		width:100%;
		height:auto;
}
</style>