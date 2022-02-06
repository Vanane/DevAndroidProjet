import { createRouter, createWebHistory } from 'vue-router'
import ComposterList from './components/routes/ComposterList.vue'	
import AboutPage from './components/routes/AboutPage.vue'
 

const routes = [
	{
		path: '/list',
		name: 'ComposterList',
		component: ComposterList
	},
	{
		path: '/about',	
		name: 'About',	
		component: AboutPage
	}	
]

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes
})
	
export default router