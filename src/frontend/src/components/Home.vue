<template>
  <div class="home">
    <b-navbar toggleable="lg" type="dark" variant="info">
      <b-navbar-brand href="/">Cities</b-navbar-brand>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-form v-on:submit.prevent>
            <b-form-input
              v-model="name"
              size="sm"
              class="mr-sm-2"
              placeholder="Search"
              @input="filter(name)"
            ></b-form-input>
          </b-nav-form>
          <b-nav-item-dropdown v-if="isLoggedIn()" right>
            <!-- Using 'button-content' slot -->
            <template #button-content>
              <em>{{ user }}</em>
            </template>
            <b-dropdown-item href="/logout">Sign Out</b-dropdown-item>
          </b-nav-item-dropdown>
          <b-nav-item v-else href="/login" right>Login</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <h1>Cities</h1>
    <b-container>
      <b-row class="mb-4 justify-content-md-center">
        <b-col cols="8"> </b-col>
      </b-row>
      <b-row align-v="center">
        <city-card
          v-for="city in cities"
          :key="city.id"
          :id="city.id"
          :name="city.name"
          :photo="city.photo"
          :hasEdit="hasEdit()"
        ></city-card>
        <!-- Use text in props -->
      </b-row>
      <b-row class="justify-content-md-center">
        <b-col cols="12" md="auto">
          <b-pagination
            v-model="currentPage"
            :total-rows="rows"
            :per-page="pageSize"
            first-text="First"
            prev-text="Prev"
            next-text="Next"
            last-text="Last"
            @input="paginate(currentPage)"
          ></b-pagination>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import Vue from "vue";
import VueCookies from 'vue-cookies';
import CityCard from "@/components/CityCard.vue";

Vue.use(VueCookies);

export default {
  name: "Home",
  components: {
    "city-card": CityCard,
  },
  data() {
    return {
      cities: [],
      currentPage: 1,
      rows: 1,
      pageSize: 6,
      name: "",
      user: "",
      edit: ""
    };
  },
  mounted() {
    this.fetchData(this.currentPage, this.name);
    this.user = this.$cookies.get("user");
    this.edit = this.$cookies.get("hasEdit") === 'true';
  },
  methods: {
    fetchData(currentPage, name) {
      this.currentPage = currentPage;
      this.name = name;

      let query = new URLSearchParams({
        page: currentPage - 1,
        size: this.pageSize,
        name: name,
      }).toString();

      fetch("api/cities?" + query)
        .then((response) => response.json())
        .then((data) => {
          this.cities = data.content;
          this.rows = data.totalElements;
          this.pageSize = data.size;
        });
    },
    paginate(currentPage) {
      this.fetchData(currentPage, this.name);
    },
    filter(name) {
      this.fetchData(1, name);
    },
    isLoggedIn() {
      return this.user;
    },
    hasEdit() {
      return this.edit;
    }
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped></style>
