<template>
  <v-app>
    <v-main style="background-color: #f5f5f5; margin: 0;">
      <v-container max-width="1200px">
        <v-row class="mb-8" align="center">
          <v-col cols="12">
            <h1 class="text-h3 font-weight-bold mb-2" style="color: #3f51b5;">
              Bane — Clients
            </h1>
            <p class="text-subtitle-1" style="color: #65748b;">
              Manage your legacy and migrated clients seamlessly.
            </p>
          </v-col>
        </v-row>


        <h2 class="text-h5 font-weight-bold mb-4" style="color: #3f51b5;">
          Legacy Clients
        </h2>
        <LegacyTable />

        <h2 class="text-h5 font-weight-bold mb-4" style="color: #3f51b5;">
          Migrated Clients
        </h2>
        <MigratedList />


        <v-snackbar v-model="snackbar.show" :timeout="4000" color="success" shaped rounded elevation="3" top right>
          {{ snackbar.msg }}
        </v-snackbar>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import LegacyTable from './components/LegacyTable.vue'
import MigratedList from './components/MigratedList.vue'

const snackbar = ref({ show: false, msg: '' })

function showSnackbar(msg) {
  snackbar.value = { show: true, msg }
}

function handleMigrationEvent(e) {
  showSnackbar(e.detail)
}

onMounted(() => {
  window.addEventListener('bane-migrated', handleMigrationEvent)
})

onUnmounted(() => {
  window.removeEventListener('bane-migrated', handleMigrationEvent)
})
</script>
