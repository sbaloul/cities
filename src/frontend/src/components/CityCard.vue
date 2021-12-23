<template>
  <div>
    <b-col>
      <b-card
        :title="name"
        :img-src="photo"
        img-alt="Image"
        img-top
        tag="article"
        class="mb-2 b-card"
        md="3"
      >
      </b-card>
    </b-col>
  </div>
</template>

<script>
export default {
  name: "CityCard",
  props: ["name", "photo"]
};
</script>

<style scoped>
.b-card {
    max-width: 20rem;
    min-width: 20rem;
}
</style>