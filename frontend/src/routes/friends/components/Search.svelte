<script lang="ts">
    import SearchResultList from "./SearchResultList.svelte";
    import type {UserSearchResult} from "$lib/common/types/user-search-result";
    import SearchBar from "./SearchBar.svelte";
    import {get} from "$lib/api";
    import {AuthorizationService} from "$lib/auth";

    const USERS_SEARCH_PATH = "users/search";

    let query: string = '';
    let searchResults: UserSearchResult[] = [];

    const handleQueryInput = async () => {
        const params = new URLSearchParams({
            "search": query,
            "page": 0,
            "size": 5
        });

        await get(`${USERS_SEARCH_PATH}?${params}`, AuthorizationService.getInstance().getToken())
            .then(response => response.json() as UserSearchResult[])
            .then(data => searchResults = data);
    }
</script>

<div class="search-container">
    <SearchBar bind:value={query} onInput={handleQueryInput} />
    {#if searchResults.length > 0}
        <div class="results-container">
            <SearchResultList bind:results={searchResults} />
        </div>
    {/if}
</div>

<style>
    .search-container {
        position: relative;
        width: 40%;
        height: 50%;
    }

    .results-container {
        position: absolute;
        top: 100%;
        width: 100%;
        z-index: 10;
    }
</style>