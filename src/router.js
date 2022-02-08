import { createRouter, createWebHistory } from 'vue-router'
import Index from './components/routes/Index.vue'	
import BookList from './components/routes/BookList.vue'	
import BookDetail from './components/routes/BookDetail.vue'	
import WatchList from './components/routes/WatchList.vue'
 

const routes = [
	{
		path: '/',
		name: 'Index',
		component: Index
	},
	{
		path: '/mylist',
		name: 'List',
		component: WatchList
	},
	{
		path: '/books',
		name:'BookList',
		component: BookList
	},
	{
		path: '/book/:id',
		name: 'BookDetail',
		component: BookDetail
	}
]

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes
})
	
export default router