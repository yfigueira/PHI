<script lang="ts">
    import {Fa} from "svelte-fa";
    import {ColorPalette} from "$lib/constants";

    export let type: string = 'text';
    export let value: string | null;
    export let placeholder: string | undefined;
    export let icon: any;

    function typeAction(node: any) {
        node.type = type;
    }
</script>

<div class="input-container">
    {#if icon}
        <Fa {icon}
            color={ColorPalette.GRAY}
            size={`${1.2}x`} />
    {/if}
    <input use:typeAction {placeholder}  bind:value on:change on:input />
</div>

<style>
    .input-container {
        background-color: var(--white);
        border: 1px solid var(--dark-gray);
        border-radius: 50px;
        height: 100%;
        display: flex;
        justify-content: start;
        align-items: center;
        padding-left: 20px;
        gap: 20px;
    }

    input {
        width: 80%;
        height: 100%;
        text-overflow: ellipsis;
    }

    input:focus {
        outline: none;
    }
</style>