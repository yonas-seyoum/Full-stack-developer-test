<template>
  <v-card class="pa-6 elevation-3" style="border-radius: 16px;">
    <v-data-table
      :headers="headers"
      :items="clients"
      item-value="id"
      class="mx-auto"
      dense
      :loading="loading"
      loading-text="Loading clients..."
      hide-default-footer
    >

      <template #item.migrated="{ item }">
        <v-chip
          :color="item.migrated ? 'success' : 'error'"
          size="small"
          class="white--text text-center"
          rounded
        >
          {{ item.migrated ? 'Yes' : 'No' }}
        </v-chip>
      </template>

      <template #item.actions="{ item }">
        <v-btn
          size="small"
          color="primary"
          :disabled="item.migrated"
          class="mx-auto"
          @click="migrate(item)"
        >
          Migrate
        </v-btn>
      </template>

      <template #no-data>
        No legacy clients found.
      </template>
    </v-data-table>
  </v-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const headers = ref([
  { title: 'ID', key: 'id', align: 'center' },
  { title: 'Name', key: 'name', align: 'center' },
  { title: 'Migrated', key: 'migrated', align: 'center' },
  { title: 'Actions', key: 'actions', align: 'center' },
])

const clients = ref([])
const loading = ref(false)

async function fetchClients() {
  loading.value = true
  try {
    const res = await axios.get('http://localhost:8080/api/legacy/clients')
    clients.value = Array.isArray(res.data) ? res.data : []
  } catch (err) {
    console.error('Failed to fetch clients', err)
    clients.value = []
  } finally {
    loading.value = false
  }
}

async function migrate(client) {
  try {
    const res = await axios.post(`http://localhost:8080/api/migrate/${client.id}`)
    await fetchClients()
    window.dispatchEvent(
      new CustomEvent('bane-migrated', { detail: res.data.message || 'Migrated successfully' })
    )
  } catch (err) {
    const message = err.response?.data?.error || 'Migration failed'
    window.dispatchEvent(new CustomEvent('bane-migrated', { detail: message }))
  }
}

onMounted(fetchClients)
</script>
