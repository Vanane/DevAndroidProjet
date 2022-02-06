<template>
    <div>
		<button class="button is-success" @click="composteurs.push({id: ++composterCount})">Ajouter un composteur</button>
		<button class="button is-danger" v-if="composteurs.length > 0" @click="composteurs.shift()">Supprimer le composteur {{composteurs[0].id}}</button>
		<br>
		<br>
		<div id="composteurs" class="columns is-multiline">
			<Composteur id="" class="column is-full-mobile is-half-tablet is-one-third-desktop is-one-quarter-widescreen is-one-quarter-fullhd"
				v-for="c in composteurs"
				v-bind:key="c.id"
				:idc="c.id"
			/>
		</div>
    </div>
</template>

<script>
import Composteur from "../Composteur.vue";
export default {
    name:"ComposterList",
    components:
    {
        Composteur
    },
    data()
    {
        return {
			composteurs: [],
			composterCount: 0
		}
    }
}
</script>