<script lang="ts">
    import type {NavigationElement} from "$lib/common/types/navigation-element";
    import Fa from "svelte-fa";

    export let elements: NavigationElement[] = [];
</script>

<div class="navigation-container">
    <ul class="navigation-bar">
        {#each elements as element}
            <li>
                <a href={element.route}>
                    <div>
                        <Fa icon={element.icon}
                            size={`${1.2}x`}
                            class="icon" />
                    </div>
                    <div>{element.label}</div>
                </a>
            </li>
        {/each}
    </ul>
</div>

<style>
    .navigation-container {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        height: 100%;
        width: 100%;
    }

    ul {
        width: 100%;
        height: 80%;
        display: flex;
        flex-direction: column;
        gap: 15px;
    }

    li {
        border: 1px solid var(--light-gray);
        height: 15%;
        width: 100%;
        border-radius: 20px;
        transition: color 200ms;
    }

    li:hover {
        transform: scale(1.015);
        border-color: var(--light-purple);
    }

    a {
        display: grid;
        grid-template-columns: 30% 70%;
        justify-content: start;
        align-items: center;
        height: 100%;
        padding-left: 20%;
        font-size: 1.3em;
        font-weight: 300;
        color: var(--dark-gray);
        transition: color 200ms;
    }

    a:hover {
        color: var(--light-purple);
    }

    .icon {
        color: var(--dark-gray);
        transition: color 200ms;
    }

    .icon:hover {
        color: var(--light-purple);
    }
</style>