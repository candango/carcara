Displaying configuration {$conf->getName()}:

  Name:     {$conf->getName()}
  Type:     {$conf->getType()}
  Host:     {$conf->getHost()}
  Database: {$conf->getDatabase()}
{if $conf->getType()=="pgsql"}
  Schema:   {$conf->getSchema()}
{/if}
  User:     {$conf->getUser()}
  Password: {$conf->getPassword()}
  File:     {$conf->getFilePath()}

