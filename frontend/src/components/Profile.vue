<template>
  <div>
    <NavigationBar />
  <v-container>
    
    <v-dialog v-model="dialog" persistent max-width="600px" min-width="360px">
      <div>
        <v-tabs
          v-model="tab"
          show-arrows
          background-color="deep-purple accent-4"
          icons-and-text
          dark
          grow
        >
          <v-tab-item>
            <v-card class="px-4">
              <v-card-text>
                <v-form ref="loginForm" v-model="valid" lazy-validation>
                  <v-row>
                    <v-col cols="12">
                      <div>Username</div>
                      <p class="display-1 text--primary">
                        {{ username }}
                      </p>
                      <br>
                      <div>First Name</div>
                      <p class="display-1 text--primary">
                        {{ firstName }}
                      </p>
                      <br>
                      <div>Last Name</div>
                      <p class="display-1 text--primary">
                        {{ lastName }}
                      </p>
                      <br>
                      <div>Age</div>
                      <v-text-field v-model="age" outlined></v-text-field>
                      <br>
                      <div>Activities</div>
                       <v-select
                        :items="items"
                        label="Select activities type"
                        outlined
                      ></v-select>
                    </v-col>

                    <v-col class="d-flex" cols="12" sm="6" xsm="12"> </v-col>
                    <v-spacer></v-spacer>
                    <v-col class="d-flex" cols="12" sm="3" xsm="12" align-end>
                      <v-btn
                        x-large
                        block
                        :disabled="!valid"
                        color="success"
                        @click="validateLogin"
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
    </v-dialog>
  </v-container>
  </div>
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
  computed: {
    // passwordMatch() {
    //   return () =>
    //     this.password === this.password_verify || "Password must match";
    // },
  },

  methods: {
    validateLogin() {
      if (this.$refs.loginForm.validate()) {
        axios
          .post(API_PATH + "/login", {
            username: this.username,
            password: this.password,
          })
          .then((res) => {
            console.log(res);
            this.success_message = res.data["message"];
            this.status_success = true;
            this.$router.push({ name: 'Map' })
          })
          .catch((error) => {
            this.error_message = error.response.data["message"];
            this.status_error = true;
          });
      }
    },
    // reset() {
    //   this.$refs.form.reset();
    // },
    // resetValidation() {
    //   this.$refs.form.resetValidation();
    // },
  },

  data: () => ({
    status_error: false,
    error_message: "",
    status_success: false,
    success_message: "",
    dialog: true,
    tab: 0,
    valid: true,

    firstName: "aaaaaa",
    lastName: "bbbbbb",
    username: "aa",
    age: 25,
    items: ['Cultural', 'Enternainment'],

    show1: false,
    rules: {
      required: (value) => !!value || "Required.",
      min: (v) => (v && v.length >= 5) || "Min 5 characters",
      max: (v) => (v && v.length <= 20) || "Max 20 characters",
    },
  }),
};
</script>

<style>
</style>
