#!/usr/bin/env bash
increment_version ()
{
  declare -a part=( ${1//\./ } )
  declare    new
  declare -i carry=1

  for (( CNTR=${#part[@]}-1; CNTR>=0; CNTR-=1 )); do
    len=${#part[CNTR]}
    new=$((part[CNTR]+carry))
    [ ${#new} -gt $len ] && carry=1 || carry=0
    [ $CNTR -gt 0 ] && part[CNTR]=${new: -len} || part[CNTR]=${new}
  done
  new="${part[*]}"
  echo -e "${new// /.}"
}
git config --global user.email "build@travis-ci.com"
git config --global user.name "Travis CI"
fetch=$(git fetch --tags)
latestTag=$(git tag --points-at master)
newPatchRelease=$(increment_version $latestTag)
git tag newPatchRelease
git push --tags