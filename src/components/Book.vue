<template>
	<div class="card">
	<div class="card-image">		
		<figure v-if="bookCover != null && bookCover != ''" class="image is-4by3">
			<img v-bind:src="bookCover" alt="Book cover">
		</figure>
	</div>
	<div class="card-content">
		<div class="media">
			<div class="media-content">
				<p class="title is-4">{{bookTitle}}</p>
				<p class="subtitle is-6"><a v-bind:href="'books?author='+bookAuthor">{{bookAuthor}}</a></p>
			</div>
		</div>
		<div class="content">{{bookResume}}</div>
	</div>
</div>
</template>

<script>
	export default {
		name: 'Book',
		props: {
			bookTitle : String,
			bookAuthor : String,
			bookDate : Date,
			bookEditor : String,
			bookResume : String,
			bookCover : String
		},
		computed: {},
		mounted() {},
		methods: {}
	}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>

</style>