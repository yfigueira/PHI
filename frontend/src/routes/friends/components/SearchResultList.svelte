<script lang="ts">
    import type {UserSearchResult} from "$lib/common/types/user-search-result";
    import SearchResult from "./SearchResult.svelte";
    import Button from "$lib/form-elements/Button.svelte";
    import {Fa} from "svelte-fa";
    import {faXmark} from "@fortawesome/free-solid-svg-icons";

    export let results: UserSearchResult[] = [];

    const handleClose = () => {
        results = [];
    }
</script>

<div class="search-results-container">
    <div class="close-button-container">
        <Button on:click={handleClose}>
            <Fa icon={faXmark} />
        </Button>
    </div>
    <ul>
        {#each results as result (result.id)}
            <li>
                <SearchResult data={result} />
            </li>
        {/each}
    </ul>
</div>

<style>
    .search-results-container {
        border: 1px solid var(--dark-gray);
        border-radius: 10px;
        width: 100%;
        background-color: var(--white);
    }

    .close-button-container {
        color: var(--dark-gray);
        display: flex;
        justify-content: end;
        align-items: center;
        padding: 10px;
    }

    li {
        border-top: 1px solid var(--light-gray);
    }
</style>