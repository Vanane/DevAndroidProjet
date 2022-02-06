import { createRouter, createWebHistory } from 'vue-router'
import ExampleComposterList from './components/routes/ExampleComposterList.vue'	
import BookList from './components/routes/BookList.vue'	
 

const routes = [
	{
		path: '/list',
		name: 'ExampleComposterList',
		component: ExampleComposterList
	},
	{
		path: '/books',
		name:'BookList',
		component: BookList
	}
]

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes
})
	
export default router