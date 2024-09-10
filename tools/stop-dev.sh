cd ./backend || return

case "$1" in
  "" | "-a" | "--all")
    docker-compose -f compose-keycloak-dev.yaml down
    docker-compose -f compose-dev.yaml down
    ;;
  "-kc" | "--keycloak")
    docker-compose -f compose-keycloak-dev.yaml down
    ;;
  "-db" | "--database")
    docker-compose -f compose-dev.yaml down
    ;;
  *)
    echo "unknown option:" "$1"
    ;;
esac
