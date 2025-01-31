<template>
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
          <v-tabs-slider color="purple darken-4"></v-tabs-slider>
          <v-tab v-for="i in tabs" :key="i.name">
            <v-icon large>{{ i.icon }}</v-icon>
            <div class="caption py-1">{{ i.name }}</div>
          </v-tab>
          <v-tab-item>
            <v-card class="px-4">
              <v-card-text>
                <v-form ref="loginForm" v-model="valid" lazy-validation>
                  <v-alert
                    dense
                    v-show="status_success"
                    type="success"
                    elevation="4"
                  >
                    {{ success_message }}
                  </v-alert>
                  <v-alert
                    dense
                    v-show="status_error"
                    type="error"
                    elevation="4"
                  >
                    {{ error_message }}
                  </v-alert>
                  <v-row>
                    <v-col cols="12">
                      <v-text-field
                        v-model="username"
                        :rules="[rules.required, rules.min, rules.max]"
                        label="Username"
                        required
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field
                        v-model="password"
                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                        :rules="[rules.required]"
                        :type="show1 ? 'text' : 'password'"
                        name="input-10-1"
                        label="Password"
                        counter
                        required
                        @click:append="show1 = !show1"
                      ></v-text-field>
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
                        Login
                      </v-btn>
                    </v-col>
                  </v-row>
                </v-form>
              </v-card-text>
            </v-card>
          </v-tab-item>
          <v-tab-item>
            <v-card class="px-4">
              <v-card-text>
                <v-form ref="registerForm" v-model="valid" lazy-validation>
                  <v-alert
                    dense
                    v-show="status_success"
                    type="success"
                    elevation="4"
                  >
                    {{ success_message }}
                  </v-alert>
                  <v-alert
                    dense
                    v-show="status_error"
                    type="error"
                    elevation="4"
                  >
                    {{ error_message }}
                  </v-alert>
                  <v-row>
                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-model="firstName"
                        :rules="[rules.required]"
                        label="First Name"
                        maxlength="20"
                        required
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12" sm="6" md="6">
                      <v-text-field
                        v-model="lastName"
                        :rules="[rules.required]"
                        label="Last Name"
                        maxlength="20"
                        required
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field
                        v-model="username"
                        label="Username"
                        :rules="[rules.required, rules.min, rules.max]"
                        required
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field
                        v-model="password"
                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                        :rules="[rules.required]"
                        :type="show1 ? 'text' : 'password'"
                        name="input-10-1"
                        label="Password"
                        counter
                        @click:append="show1 = !show1"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field
                        block
                        v-model="password_verify"
                        :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                        :rules="[rules.required, passwordMatch]"
                        :type="show1 ? 'text' : 'password'"
                        name="input-10-1"
                        label="Confirm Password"
                        counter
                        @click:append="show1 = !show1"
                      ></v-text-field>
                    </v-col>
                    <v-spacer></v-spacer>
                    <v-col class="d-flex ml-auto" cols="12" sm="3" xsm="12">
                      <v-btn
                        x-large
                        block
                        :disabled="!valid"
                        color="success"
                        @click="validateRegister"
                        >Register</v-btn
                      >
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
</template>

<script>
import axios from "axios";
const API_PATH = "http://localhost:8081/api";

export default {
  name: "Login",
  mounted() {
    localStorage.clear();
  },
  computed: {
    passwordMatch() {
      return () =>
        this.password === this.password_verify || "Password must match";
    },
  },

  methods: {
    validateLogin() {
      if (this.$refs.loginForm.validate()) {
        axios
          .post(API_PATH + "/login", {
            username: this.username,
            password: this.password,
          }, {withCredentials: true})
          .then((res) => {
            console.log(res);
            localStorage.setItem("age", res.data['age'])
            localStorage.setItem("userInterests", res.data['userInterests'])
            this.$router.push({ name: 'Map' })
          })
          .catch((error) => {
            this.error_message = error.response.data["message"];
            this.status_error = true;
          });
      }
    },
    validateRegister() {
      if (this.$refs.registerForm.validate()) {
        axios
          .post(API_PATH + "/register", {
            username: this.username,
            password: this.password,
            firstName: this.firstName,
            lastName: this.lastName,
            passwordConfirm: this.password_verify,
          }, {withCredentials:true})
          .then((res) => {
            console.log(res);
            this.success_message = res.data["message"];
            this.status_success = true;
          })
          .catch((error) => {
            this.error_message = error.response.data["message"];
            this.status_error = true;
          });
      }
    },
    reset() {
      this.$refs.form.reset();
    },
    resetValidation() {
      this.$refs.form.resetValidation();
    },
  },

  data: () => ({
    status_error: false,
    error_message: "",
    status_success: false,
    success_message: "",
    dialog: true,
    tab: 0,
    tabs: [
      { name: "Login", icon: "mdi-account" },
      { name: "Register", icon: "mdi-account-outline" },
    ],
    valid: true,

    firstName: "",
    lastName: "",
    username: "",
    password: "",
    password_verify: "",
    loginPassword: "",
    loginUsername: "",

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
