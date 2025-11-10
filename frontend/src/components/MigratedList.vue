<template>
  <v-card class="pa-6 elevation-3" style="border-radius: 12px;">
    <v-data-table
      :headers="headers"
      :items="migrated"
      item-key="id"
      dense
      hide-default-footer
      class="items-center"
      :loading="loading"
      loading-text="Loading migrated clients..."
    >
      <template #no-data>
        No migrated clients yet.
      </template>
    </v-data-table>
  </v-card>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import axios from 'axios'

const migrated = ref([])
const loading = ref(false)

const headers = [
  { title: 'ID', key: 'id', align: 'center' },
  { title: 'Name', key: 'name', align: 'center' },
]

async function fetchMigrated() {
  loading.value = true
  try {
    const res = await axios.get('http://localhost:8080/api/new/clients')
    migrated.value = Array.isArray(res.data) ? res.data : []
  } catch (e) {
    console.error('Failed to fetch migrated clients', e)
    migrated.value = []
  } finally {
    loading.value = false
  }
}

function handleMigrationEvent() {
  fetchMigrated()
}

onMounted(() => {
  fetchMigrated()
  window.addEventListener('bane-migrated', handleMigrationEvent)
})

onUnmounted(() => {
  window.removeEventListener('bane-migrated', handleMigrationEvent)
})
</script>
