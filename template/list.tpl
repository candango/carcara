Data Sources:

{foreach $configs as $config}
    - {$config->getName()}:
        type: {$config->getType()}, host: {$config->getHost()}, database: {$config->getDatabase()}, user: {$config->getUser()}, password: {$config->getPassword()}
{foreachelse}
    No Data Sources defined yet. Use command init to create a new one.
{/foreach}

