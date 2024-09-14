<script lang="ts">
    import type {UserSearchResult} from "$lib/common/types/user-search-result";
    import InitialsContainer from "$lib/common/components/InitialsContainer.svelte";

    export let data: UserSearchResult;

    const {id, firstName, lastName, email} = data;
</script>

<div class="search-result-container">
    <InitialsContainer {firstName} {lastName} fontSize="1rem" diameter="40px" />
    <div class="result-data-container">
        <div>
            {`${firstName} ${lastName}`}
        </div>
        <div class="email">
            {`${email}`}
        </div>
    </div>
</div>

<style>
    .search-result-container {
        display: grid;
        grid-template-columns: 15% 85%;
        align-items: center;
        padding: 5px;
    }

    .search-result-container:hover {
        background-color: var(--light-gray);
    }

    .result-data-container {
        display: flex;
        flex-direction: column;
    }

    .email {
        font-weight: 100;
    }
</style>