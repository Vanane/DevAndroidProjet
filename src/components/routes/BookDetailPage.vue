<template>
	<div v-if="this.bookDetail">
		<BookDetail
			:bookId="this.bookDetail.id"
			:bookTitle="this.bookDetail.volumeInfo.title"
			:bookAuthor="this.bookDetail.volumeInfo.authors"
			:bookPublisher="this.bookDetail.volumeInfo.publisher"
			:bookDate="this.bookDetail.volumeInfo.publishedDate"
			:bookResume="this.bookDetail.volumeInfo.description"
			:bookCover="this.bookDetail.volumeInfo.imageLinks ? this.bookDetail.volumeInfo.imageLinks.thumbnail : null"
            :bookCategories="this.bookDetail.volumeInfo.categories ?? null"
		/>
	</div>
	<div v-else-if="this.loaded || !this.$route.params.id">
		<NotFound/>
	</div>
</template>

<script>
import BookDetail from '../BookDetail.vue';
import NotFound from '../NotFound.vue';

export default {
	name:"BookDetailRoute",
	components: {
		BookDetail, NotFound
	},
	data() {
		return {
			bookDetail: null,
			loaded : false
		}
	},

	methods:
    {
        getBookDetail() {            
            this.getBook(this.$route.params.id)
                .then(l => { this.bookDetail = l; this.loaded = true; })
        },
    },
	mounted() {
		if(this.$route.params.id)
			this.getBookDetail();
	}
}
</script>

<style>

</style>