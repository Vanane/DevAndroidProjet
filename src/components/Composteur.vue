<template>
		<div class="card" @mouseenter="horaires.pop()" 
		:style="
		{
			backgroundColor: isOpen ? '#ddffdd' : '#ffdddd'
		}">
		
	<div class="card-image">
		<figure class="image is-4by3">
			<img v-bind:src="source" alt="Placeholder image" style="mix-blend-mode: multiply">
		</figure>
	</div>
	<div class="card-content">
		<div class="media">
			<div class="media-content">
				<p class="title is-5">n°{{idc}} - {{nom}}</p>
				<p class="subtitle is-6">{{adresse}}</p>
			</div>
		</div>

		<div class="content">
			Horaires d'ouverture :
			<p v-for="h in horaires" :key="h">{{h}}</p>
		</div>
	</div>
</div>
</template>

<script>
export default {
		name:"Composter",
		props:
		{
			idc: Number
		},
		data()
		{
				var data = 
				{
						nom: "Pas de nom",
						adresse: "Pas d'adresse",
						horaires: 
						[
							"Lundi",
							"Mardi",
							"Mercredi",
							"Jeudi",
							"Vendredi"
						]
				};
				return data;
		},
		computed:
		{
			isOpen()
			{
				return this.horaires.length > 0;
			},
			source()
			{
				return "./assets/composter" + (Math.floor(Math.random() * 3) + 1) + ".jpg";
			}
		}
}
</script>

<style>

</style>