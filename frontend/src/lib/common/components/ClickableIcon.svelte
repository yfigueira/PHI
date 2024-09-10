<script lang="ts">
    import Button from "$lib/form-elements/Button.svelte";
    import Fa from "svelte-fa";

    export let iconType: string = "button" | "link";
    export let icon: any;
    export let size: number = 1;
    export let href: string | undefined;
    export let onclick: () => void | undefined;


</script>

<div class="icon-container">
    {#if iconType === "link"}
        <a {href}>
            <Fa {icon} size={`${size}x`} />
        </a>
    {:else}
        <Button on:click={onclick}>
            <Fa {icon} size={`${size}x`} />
        </Button>
    {/if}
</div>


<style>
    .icon-container {
        color: var(--dark-gray);
        transition: color 300ms;
    }

    .icon-container:hover {
        color: var(--dark-purple);
    }
</style>
