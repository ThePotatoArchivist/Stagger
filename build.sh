PROJECT=stagger-compat
VERSION=1.0

cd "$(basename $0)"
mkdir build
zip -r "build/$PROJECT-$VERSION.zip" pack.mcmeta pack.png assets data