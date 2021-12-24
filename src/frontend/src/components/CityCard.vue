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
        <b-button id="show-btn" @click="show = true">Open Modal</b-button>

        <b-modal
          v-model="show"
          ref="edit-dialog"
          title="Edit city"
          @hidden="close"
          @show="showDialog"
          @ok="save"
        >
          <form ref="form" @submit.stop.prevent="handleSubmit">
            <b-form-group
              label="Name"
              label-for="name-input"
              invalid-feedback="Name is required"
            >
              <b-form-input
                id="name-input"
                v-model="modalName"
                required
              ></b-form-input>
            </b-form-group>
            <b-form-group
              label="Photo"
              label-for="phot-input"
              invalid-feedback="Photo is required"
            >
              <b-form-input
                id="Photo-input"
                v-model="modalPhoto"
                required
              ></b-form-input>
            </b-form-group>
          </form>
        </b-modal>
      </b-card>
    </b-col>
  </div>
</template>

<script>
export default {
  name: "CityCard",
  props: ["name", "photo", "modalName", "modalPhoto"],
  data() {
    return {
      show: false,
    };
  },
  methods: {
    showDialog() {
      this.modalName = this.name;
      this.modalPhoto = this.photo;
      this.$refs["edit-dialog"].show();
    },
    close() {
      this.$refs["edit-dialog"].hide();
    },
    save() {
      this.name = this.modalName;
      this.photo = this.modalPhoto;
      this.close();
    },
  },
};
</script>

<style scoped>
.b-card {
  max-width: 20rem;
  min-width: 20rem;
}
</style>
