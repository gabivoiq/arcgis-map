<template>
  <v-container>
    <NavigationBar />
    <iframe title="story_map" width="1800px" height="900px" src="https://learngis2.maps.arcgis.com/apps/MapTour/index.html?appid=2948ee98fa784d26bb54882cf3c316df"></iframe>
  </v-container>
</template>

<script>
import NavigationBar from "@/components/NavigationBar";

export default {
  name: "StoryMap",
  components: {
    NavigationBar
  }
}
</script>

<style scoped>

</style>