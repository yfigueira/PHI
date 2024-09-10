<script lang="ts">
    import "../app.css"
    import '@fontsource-variable/inter';

    import type {PageData} from "./$types";

    import {user} from "../stores/userStore";

    import {faGithub, faLinkedinIn} from "@fortawesome/free-brands-svg-icons";
    import {faHouse, faUser, faUserGroup} from "@fortawesome/free-solid-svg-icons";
    import {faComments} from "@fortawesome/free-regular-svg-icons";

    import type {SocialMediaLink} from "$lib/common/types/social-media-link";
    import {CreatorSocialMedia} from "$lib/constants";
    import type {NavigationElement} from "$lib/common/types/navigation-element";

    import UserSummary from "./UserSummary.svelte";
    import Footer from "./Footer.svelte";
    import Navigation from "./Navigation.svelte";

    export let data: PageData;
    user.set(data.userData)

    let socialMediaLinks: SocialMediaLink[] = [
        {icon: faLinkedinIn, href: CreatorSocialMedia.LINKEDIN},
        {icon: faGithub, href: CreatorSocialMedia.GITHUB}
    ]

    let navigationElements: NavigationElement[] = [
        {icon: faHouse, label: "Home", route: "/home"},
        {icon: faUser, label: "Profile", route: "/profile"},
        {icon: faUserGroup, label: "Friends", route: "/network"},
        {icon: faComments, label: "Chat", route: "/chat"},
    ]
</script>

<div class="main-layout" >
    <div class="side-panel left-panel">
        <UserSummary userSummary={data.userData} />
        <Navigation elements={navigationElements} />
        <Footer bind:socialMediaLinks={socialMediaLinks} />
    </div>
    <div class="central-panel">
        <slot />
    </div>
    <div class="side-panel right-panel"></div>
</div>

<style>
    .main-layout {
        display: grid;
        grid-template-columns: 18% 64% 18%;
        height: 100%;
    }

    .side-panel {
        background-color: var(--white);
    }

    .central-panel {
        padding: 20px;
        background-color: var(--light-gray);
        z-index: -1;
    }

    .right-panel {
        box-shadow: -5px 0 5px var(--dark-gray);
    }

    .left-panel {
        padding: 5px;
        display: grid;
        grid-template-rows: 20% 60% 20%;
        box-shadow: 5px 0 5px var(--dark-gray);
    }
</style>