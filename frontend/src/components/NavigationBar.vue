<template>
  <div
    class="mdi-auto"
    height=100%
    width=50%
  >
    <v-navigation-drawer
      expand-on-hover
      v-model="sidebar"
      app
      class="deep-purple accent-4"
      dark
      permanent
    >
      <v-list>
        <v-list-item
          v-for="item in items"
          :key="item.title"
          :to="item.path"
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

  </div>
</template>

<script>
  export default {
    data () {
      return {
        name: 'NavigationBar',
        items: [
          { title: 'Profile', icon: 'mdi-account', path: '/profile' },
          { title: 'Storymap', icon: 'mdi-map-outline', path: '/storymap' },
          { title: 'Map', icon: 'mdi-map-marker', path: '/map' },
        ],
      }
    },
  }
</script>