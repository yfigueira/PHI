<script lang="ts">
    export let type: 'button' | 'reset' | 'submit' | null | undefined = 'button';
    export let disabled: boolean | undefined | null;
    export let className: string;
</script>

<button {type} {disabled} on:click class={className}>
    <slot />
</button>