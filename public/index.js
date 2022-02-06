import ComposterList from '../components/routes/CompostersList.vue'	
import AboutPage from '../components/routes/AboutPage.vue'
 
	
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