<template>
  <v-container>
    <NavigationBar />
    <v-card class="mx-auto overflow-hidden" max-height="800px" max-width="500px">
      <div>
        <v-tabs
          v-model="tab"
          show-arrows
          background-color="deep-purple accent-4"
          icons-and-text
          dark
          grow
        >
          <v-tab>User profile</v-tab>
          <v-tab-item >
            <v-card class="px-4">
              <v-card-text>
                <v-form ref="loginForm" v-model="valid" lazy-validation>
                  <v-row>
                    <v-col cols="12">
                      <div>Username</div>
                      <v-text-field v-model="username" readonly></v-text-field>
                      <br>
                      <div>First Name</div>
                      <v-text-field v-model="firstName" readonly></v-text-field>
                      <br>
                      <div>Last Name</div>
                      <v-text-field v-model="lastName" readonly></v-text-field>
                      <br>
                      <div>Age</div>
                      <v-text-field v-model="age" outlined></v-text-field>
                      <br>
                      <div>Activities</div>
                       <v-select
                           v-model="userInterests"
                           :items="items"
                           multiple
                           hint="Pick your favorite activities"
                           outlined
                           persistent-hint
                      ></v-select>
                    </v-col>
                    <v-alert
                        dense
                        v-show="status_success"
                        type="success"
                        elevation="4"
                        dismissible
                    >
                      {{ success_message }}
                    </v-alert>

                    <v-col class="d-flex" cols="12" sm="6" xsm="12"> </v-col>
                    <v-spacer></v-spacer>
                    <v-col class="d-flex" cols="12" sm="3" xsm="12" align-end>
                      <v-btn
                        x-large
                        block
                        :disabled="!valid"
                        color="success"
                        @click="updateUserData"
                      >
                        Save
                      </v-btn>
                    </v-col>
                  </v-row>
                </v-form>
              </v-card-text>
            </v-card>
          </v-tab-item>
        </v-tabs>
      </div>
    </v-card>
  </v-container>
</template>

<script>
import axios from "axios";
import NavigationBar from '@/components/NavigationBar';
const API_PATH = "http://localhost:8081/api";


export default {
  name: "Profile",
  components: {
    NavigationBar
  },
  mounted() {
    this.getUserData()
  },

  methods: {
    updateUserData() {
      axios
          .put(API_PATH + "/profile", {
            username: this.username,
            firstName: this.firstName,
            lastName: this.lastName,
            age: this.age,
            userInterests: this.userInterests
          })
          .then((res) => {
            console.log(res);
            this.error_message = false;
            this.status_success= true;
            this.getUserData();
            localStorage.setItem("age", this.age);
            localStorage.setItem("userInterests", this.userInterests);
          })
          .catch((error) => {
            let msg = error.response.data["message"]
            if(!msg) {
              this.error_message = "Error processing the request";
            } else {
              this.error_message = msg;
            }
            this.display_error = true;
          });
    },
    getUserData() {
      axios.get(API_PATH + "/profile")
          .then(response => {
            this.lastName = response.data['lastName'];
            this.firstName = response.data['firstName'];
            this.username = response.data['username'];
            this.age = response.data['age'];
            this.userInterests = response.data['userInterests'];
          })
    }
  },

  data: () => ({
    userInterests: [],
    display_error: false,
    error_message: "",
    status_success: false,
    success_message: "User profile was saved successfully",
    dialog: true,
    tab: 0,
    valid: true,

    firstName: '',
    lastName: '',
    username: '',
    age: 0,
    items: ['Cultural', 'Entertainment'],
  }),
};
</script>

<style>
</style>
