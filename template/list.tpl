Carcara confs:

{foreach $confs as $conf}
    - {$conf->getName()}:
        type: {$conf->getType()}, host: {$conf->getHost()}, database: {$conf->getDatabase()}, user: {$conf->getUser()}, password: {$conf->getPassword()}
{foreachelse}
    No Data Sources defined yet. Use command init to create a new one.
{/foreach}
