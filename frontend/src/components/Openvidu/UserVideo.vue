<template>
<div id="UserVideo" v-if="streamManager">
	<ov-video :stream-manager="streamManager"/>
	<div id="UserName"><p>{{ clientData }}</p></div>
</div>
</template>

<script>
import OvVideo from './OvVideo';

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
	},

	computed: {
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
};
</script>
<style>
	#UserVideo {
		display: inline-block;
		border: 1px black solid;
		margin: 5px;
	}

	#UserName {
		border: 1px black solid;
		border-radius: 10%;
	}
</style>