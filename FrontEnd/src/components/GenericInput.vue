<template>
  <q-field
    borderless
    stack-label
    :label="label"
    color="primary"
    :dense="isDense"
    :error="modelError"
    class="q-my-none"
  >
    <template v-slot:control>
      <q-input
        outlined
        filled
        :aria-label="label"
        :type="inputType"
        v-model="modelValueLocal"
        @input="updateValue"
        :hint="hint"
        :mask="mask"
        fill-mask
        :rules="rules"
        class="full-width q-my-sm"
      >
        <template v-if="iconPrepend" v-slot:prepend>
          <q-icon :name="iconPrepend"></q-icon>
        </template>
        <template v-if="iconAppend" v-slot:append>
          <q-icon :name="iconAppend" />
        </template>
      </q-input>
      <div v-if="modelError" class="text-negative">
        {{ modelError }}
      </div>
    </template>
  </q-field>
</template>
<script lang="ts">
import { defineComponent, PropType, ref, watch } from 'vue';

export default defineComponent({
  name: 'GenericInput',
  props: {
    label: {
      type: String,
      required: true,
    },
    modelValue: {
      type: [String, Number] as PropType<string | number>,
      required: true,
    },
    inputType: {
      type: String as PropType<
        | 'text'
        | 'number'
        | 'email'
        | 'password'
        | 'tel'
        | 'url'
        | 'date'
        | 'datetime-local'
      >,
      default: 'text',
    },
    hint: String,
    mask: String,
    iconAppend: String,
    iconPrepend: String,
    isDense: Boolean,
    rules: {
      type: Array as PropType<Array<(value: string) => boolean | string>>,
      required: true,
    },
  },

  setup(props, { emit }) {
    const modelError = ref<boolean>();
    const modelValueLocal = ref(props.modelValue);

    watch(
      () => props.modelValue,
      (newValue) => {
        modelValueLocal.value = newValue;
      }
    );

    const updateValue = (value: string | number | Date) => {
      emit('update:modelValue', value);
    };

    return {
      modelError,
      modelValueLocal,
      updateValue,
    };
  },
});
</script>
