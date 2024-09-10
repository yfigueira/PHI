<script lang="ts">
    import {AuthorizationService} from "$lib/auth";
    import Fa from 'svelte-fa'
    import {faPowerOff} from "@fortawesome/free-solid-svg-icons";
    import type {SocialMediaLink} from "$lib/common/types/social-media-link";
    import ClickableIcon from "$lib/common/components/ClickableIcon.svelte";
    import {IconType} from "$lib/common/types/clickable-icon";

    export let socialMediaLinks: SocialMediaLink[];

    const authorizationService: AuthorizationService = AuthorizationService.getInstance();
    const handleSignOut = () => {
        authorizationService.logout();
    }
</script>

<div class="footer-container">
    <ClickableIcon iconType={IconType.BUTTON}
                   icon={faPowerOff}
                   size={3}
                   onclick={handleSignOut} />
    <div class="links-container">
        Created by:
        {#each socialMediaLinks as link}
            <ClickableIcon iconType={IconType.LINK}
                           icon={link.icon}
                           href={link.href}
                           size={1.5} />
        {/each}
    </div>
</div>

<style>
    .footer-container {
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        align-items: center;
        color: var(--dark-gray);
    }

    .links-container {
        width: 50%;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }
</style>