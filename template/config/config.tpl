<?php return array (
    "name" => "{$config->getName()}",
    "type" => "{$config->getType()}",
    "host" => "{$config->getHost()}",
    "database" => "{$config->getDatabase()}",
{if $config->getType()=="pgsql"}
    "schema" => "{$config->getSchema()}",
{/if}
    "user" => "{$config->getUser()}",
    "password" => "{$config->getPassword()}"
);
