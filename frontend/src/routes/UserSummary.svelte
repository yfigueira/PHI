<script lang="ts">
    import InitialsContainer from "$lib/common/components/InitialsContainer.svelte";

    export let userSummary;

    const {firstName, lastName, email} = userSummary;
</script>

<div class="user-summary-container">
    <InitialsContainer {firstName} {lastName} />
    <div class="name-container">
        {`${firstName} ${lastName}`}
    </div>
    <div class="email-container">
        {email}
    </div>
</div>

<style>
    .user-summary-container {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        font-size: large;
    }

    .name-container {
        font-size: x-large;
    }

    .email-container {
        font-weight: 100;
    }
</style>