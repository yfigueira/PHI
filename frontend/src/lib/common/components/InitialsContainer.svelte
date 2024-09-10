<script lang="ts">

    export let firstName: string = "?";
    export let lastName: string = "?";
    export let color: string | undefined;
    export let diameter: string | undefined;
    export let fontSize: string | undefined

    const initials = firstName.substring(0,1) + lastName.substring(0,1);
</script>

<div class="initials-container"
     style="--color:{color}; --diameter:{`${diameter || '65px'}`}; --font-size:{`${fontSize || '1.5em'}`}" >
    {initials}
</div>

<style>
    .initials-container {
        color: var(--color, var(--dark-purple));
        border: 2px solid;
        width: var(--diameter);
        height: var(--diameter);
        border-radius: 50%;
        padding: 6px;
        font-size: var(--font-size);
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>

