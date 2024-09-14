<script lang="ts">
    import {faMagnifyingGlass} from "@fortawesome/free-solid-svg-icons";
    import Input from "$lib/form-elements/Input.svelte";

    export let value: string;
    export let onInput: () => {};

</script>


<div class="search-input">
    <Input bind:value placeholder="Search..." icon={faMagnifyingGlass} on:input={onInput} />
</div>

<style>
    .search-input {
        height: 100%;
    }
</style>