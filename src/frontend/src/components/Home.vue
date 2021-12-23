<template>
  <div class="home">
    <h1>Cities</h1>
    <b-container>
      <b-row class="mb-4 justify-content-md-center">
        <b-col cols="8">
          <b-form-input
            v-model="name"
            id="input-large"
            size="lg"
            placeholder="Filter"
            @input="filter(name)"
          ></b-form-input>
        </b-col>
      </b-row>
      <b-row align-v="center">
        <city-card
          v-for="city in cities"
          :key="city.id"
          :name="city.name"
          :photo="city.photo"
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
import CityCard from "@/components/CityCard.vue";

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
    };
  },
  mounted() {
    this.fetchData(this.currentPage, this.name);
  },
  methods: {
    fetchData(currentPage, name) {
      this.currentPage = currentPage;
      this.name = name;

      let query = new URLSearchParams({
        page: currentPage - 1,
        size: this.pageSize,
        name: name
      }).toString();

      fetch("api/cities?" + query)
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
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
    }
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped></style>
