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
          @click="action(item)"
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
import axios from "axios";
const API_PATH = "http://localhost:8081/api";
  export default {
    methods: {
      logout() {
        axios.get(API_PATH + "/me/logout",{withCredentials:true})
        localStorage.clear();
        sessionStorage.clear();
      },
      action(item) {
       if(item.title === 'Logout') {
         this.logout();
       }
      }
    },
    data () {
      return {
        name: 'NavigationBar',
        items: [
          { title: 'Profile', icon: 'mdi-account', path: '/profile'},
          { title: 'Storymap', icon: 'mdi-map-outline', path: '/storymap'},
          { title: 'Map', icon: 'mdi-map-marker', path: '/map'},
          { title: 'Logout', icon: 'mdi-arrow-up-bold-box-outline', path: "/login"}
        ],
      }
    },
  }
</script>