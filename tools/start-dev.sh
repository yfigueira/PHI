cd ./backend || return

case "$1" in
  "" | "-a" | "--all")
    docker-compose -f compose-keycloak-dev.yaml up --build --detach
    docker-compose -f compose-dev.yaml up --build --detach
    ;;
  "-kc" | "--keycloak")
    docker-compose -f compose-keycloak-dev.yaml up --build --detach
    ;;
  "-db" | "--database")
    docker-compose -f compose-dev.yaml up --build --detach
    ;;
  *)
    echo "unknown option:" "$1"
    ;;
esac