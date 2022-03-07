import { createRouter, createWebHistory } from 'vue-router'
import Index from './components/routes/IndexPage.vue'	
import BookList from './components/routes/BookListPage.vue'	
import BookDetail from './components/routes/BookDetailPage.vue'	
import WatchList from './components/routes/WatchListPage.vue'
import ScanBook from './components/routes/ScanBookPage.vue'
import NotFound from './components/NotFound.vue'

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
	},
    {
        path: '/scan',
        name: 'ScanBook',
        component: ScanBook
    },
    {
        path: '/404',
        name: 'NotFound',
        component: NotFound,
        props: true
    }
]

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes
})
	
export default router