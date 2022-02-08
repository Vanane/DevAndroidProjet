<template>
	<div class="card">
	<div class="card-content">
		<div class="media">
			<div class="media-content">
				<p class="title is-4"><router-link :to="{ name: 'BookDetail', params: { id: bookId } }">{{bookTitle}}</router-link></p>
				<p class="subtitle is-6">{{bookAuthor}}</p>
			</div>
		</div>
	</div>
</div>
</template>

<script>
	export default {
		name: 'Book',
		props: {
			bookId : String,
			bookTitle : String,
			bookAuthor : Array,
			bookPublisher : String,
		},
		computed: {},
		mounted() {},
		methods: {}
	}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.bookcover {
    width: auto;
    height: 100%;
}
</style>