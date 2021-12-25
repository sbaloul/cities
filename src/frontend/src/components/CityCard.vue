<template>
    <b-col cols="4">
      <b-card
        :title="name"
        :img-src="photo"
        img-alt="Image"
        img-top
        tag="article"
        class="mb-2 b-card"
        md="3"
      >
        <b-button id="show-btn" @click="showDialog">Open Modal</b-button>

        <b-modal
          centered
          ref="edit-dialog"
          title="Edit city"
          ok-title="Save"
          @hidden="close"
          @ok="save"
        >
          <b-form ref="form" @submit.stop.prevent="save">
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
                id="photo-input"
                v-model="modalPhoto"
                required
              ></b-form-input>
            </b-form-group>
          </b-form>
        </b-modal>
      </b-card>
    </b-col>
</template>

<script>
import axios from "axios";

export default {
  name: "CityCard",
  props: ["id", "name", "photo"],
  data() {
    return {
      modalName: "",
      modalPhoto: "",
    };
  },
  methods: {
    checkFormValidity() {
      return this.$refs.form.checkValidity();
    },
    showDialog() {
      this.modalName = this.name;
      this.modalPhoto = this.photo;
      this.$refs["edit-dialog"].show();
    },
    close() {
      this.$refs["edit-dialog"].hide();
    },
    save(bvModalEvt) {
      if (!this.checkFormValidity()) {
        bvModalEvt.preventDefault();
        return;
      }

      let query = new URLSearchParams({
        name: this.modalName,
        photo: this.modalPhoto
      }).toString();

      axios.put("api/cities/" + this.id + "?" + query).then(() => {
        this.name = this.modalName;
        this.photo = this.modalPhoto;
        this.close();
      });
    },
  },
};
</script>

<style scoped>
</style>
